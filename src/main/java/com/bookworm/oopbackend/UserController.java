package com.bookworm.oopbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    protected MongoTemplate mongoTemplate;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping ("/user/auth/{user_uid}")
    public User getUser (@PathVariable String user_uid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(user_uid));
        return mongoTemplate.findOne(query, User.class);
    }

    @GetMapping ("/user/auth/get-name/{uid}")
    public User getUserName (@PathVariable String uid) {
        Query query = new Query ();
        query.addCriteria(Criteria.where("_id").is(uid));
        return mongoTemplate.findOne(query, User.class);
    }

    @GetMapping ("/user/auth/{username}/{password}")
    public User loginUser (@PathVariable String username, @PathVariable String password) {
        Query query = new Query ();
        query.addCriteria(Criteria.where("username").is(username).andOperator(Criteria.where("password").is(password)));
        return mongoTemplate.findOne(query, User.class);
    }
    @CrossOrigin (origins = "http://localhost:3000")
    @PostMapping ("/user/auth")
    public User createUser (@RequestBody User user) {
        return userRepository.save(user);
    }

}
