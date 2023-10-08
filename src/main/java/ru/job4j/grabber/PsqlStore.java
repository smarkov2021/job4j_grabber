package ru.job4j.grabber;

import ru.job4j.grabber.utils.Post;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private Connection cnn;

    public PsqlStore(Properties cfg) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Post createPost(ResultSet resultSet) throws SQLException {
        int ide = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String text = resultSet.getString("text");
        String link = resultSet.getString("link");
        Timestamp timestamp = resultSet.getTimestamp("created");
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return new Post(ide, name, text, link, localDateTime);
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement("INSERT INTO post(name, text, link, created)"
                + " values (?, ?, ?, ?) ON CONFLICT(link) DO NOTHING;")) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4,  Timestamp.valueOf(post.getCreated()));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement("select id, name, text, link, created from post")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                rsl.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post findById(int id) {
        Post rsl = null;
        try (PreparedStatement ps = cnn.prepareStatement("select  id, name, text, link, created from post"
                + " where id = ? limit 1")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                rsl = createPost(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("rabbit.properties");
        Properties cf = new Properties();
        cf.load(in);
        Store pStore = new PsqlStore(cf);
        LocalDateTime localDateTime = LocalDateTime.now();
        Post newPost = new Post(2, "test", "test22", "test3", localDateTime);
        pStore.save(newPost);
        List<Post> rsl = pStore.getAll();
        for (Post post : rsl) {
            System.out.println("args = " + post);
        }
        System.out.println("args = " + pStore.findById(1));
    }
}