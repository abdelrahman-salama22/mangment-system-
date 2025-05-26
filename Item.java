class Item {
    private String name;
    private String description;
    private String category;
    private String priority; // "urgent" or "normal"

    public Item(String name, String description, String category, String priority) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.priority = priority;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setPriority(String priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }

    public String toJson() {
        return "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"description\": \"" + description + "\",\n" +
                "    \"category\": \"" + category + "\",\n" +
                "    \"priority\": \"" + priority + "\"\n" +
                "  }";
    }
}
