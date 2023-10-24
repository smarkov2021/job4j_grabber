package ru.job4j.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String link = String.format("%s%s%s", cachingDir, "/", key);
        Path path = Paths.get(link);
        String rsl = "";
        try {
            rsl = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}