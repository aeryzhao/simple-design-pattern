package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 20:43
 */
public class Main {
    public static void main(String[] args) {
        User user = User.builder().name("jack").age(12).email("xxx@emial.com").address("shanghai").build();
        System.out.println(user);
    }
}

class User {
    private String name;
    private Integer age;
    private String email;
    private String address;

    private User(String name, Integer age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer age;
        private String email;
        private String address;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(name, age, email, address);
        }
    }

    @Override
    public String toString() {
        return "name = " + name + ", age = " + age + ", email = " + email + ", address = " + address;
    }

}
