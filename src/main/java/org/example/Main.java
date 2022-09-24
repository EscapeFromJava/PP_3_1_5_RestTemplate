package org.example;

import org.example.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String url = "http://94.198.50.185:7081/api/users";
        String result = "";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // get list of all users
        HttpEntity<Void> requestGetAllEntities = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestGetAllEntities, String.class);

        // get and set cookies
        String cookie = response.getHeaders().getFirst("set-cookie");
        headers.set("Cookie", cookie);

        // add new user
        User user = new User(3L, "James", "Brown", (byte) 33);
        HttpEntity<User> requestAddEntity = new HttpEntity<>(user, headers);
        response = restTemplate.exchange(url, HttpMethod.POST, requestAddEntity, String.class);
        result += response.getBody();

        // update user
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestUpdateEntity = new HttpEntity<>(user, headers);
        response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdateEntity, String.class);
        result += response.getBody();

        // delete user
        HttpEntity<Long> requestDeleteEntity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url.concat("/3"), HttpMethod.DELETE, requestDeleteEntity, String.class);
        result += response.getBody();

        System.out.println(result);
    }
}