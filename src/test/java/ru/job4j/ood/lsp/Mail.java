package ru.job4j.ood.lsp;

class PostLetter {
    private String address;
    private String text;
    private String index;

    public PostLetter() {
    }

    public PostLetter(String address, String text, String index) {
        this.address = address;
        this.text = text;
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public String getText() {
        return text;
    }

    public String getIndex() {
        return index;
    }
}

class EmailLetter extends PostLetter {
    private String address;
    private String text;
    private String index;

    public EmailLetter() {
        super();
    }

    public EmailLetter(String address, String text) {
        super(address, text, null);
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getIndex() {
        return null;
    }
}
