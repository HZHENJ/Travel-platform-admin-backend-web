package com.example.backendweb.Services.User    ;


import com.example.backendweb.Repository.User.AuthenticationRepository;
import com.example.backendweb.Repository.User.PreferenceRepository;
import com.example.backendweb.Repository.User.UserRepository;
import com.example.backendweb.Entity.Exception.CustomException;
import com.example.backendweb.Entity.User.Authentication;
import com.example.backendweb.Entity.User.Preference;
import com.example.backendweb.Entity.User.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PreferenceRepository preferenceRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, AuthenticationRepository authenticationRepository, PreferenceRepository preferenceRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.preferenceRepository = preferenceRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User Login(String Username,String Password){
        Optional<Authentication> data = authenticationRepository.findByUsername(Username);
        if(data.isPresent() && data.get().getPasswordHash().equals(Password)){
            return data.get().getUser();
        }else{
            throw new CustomException("User not exist or Wrong password",400);
        }
    }

    public boolean RetriveData(Authentication auth, User user, Preference pre){
        return registerUser(user) && registerAuthentication(auth,user) && registerPreference(pre,user);
    }

    public boolean registerUser(User user){
        if(user == null) throw new CustomException("Empty Data",400);
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if(existingUser.isPresent()) throw new CustomException("User already exits",409);
        userRepository.save(user);
        return true;
    }

    public boolean registerAuthentication(Authentication auth,User user){
        if(auth == null || user == null) throw new CustomException("Empty Data",400);
        Optional<Authentication> existingAuth = authenticationRepository.findById(auth.getAuthId());
        if(existingAuth.isPresent()) throw new CustomException("User already exits",409);
        auth.setPasswordHash(bCryptPasswordEncoder.encode(auth.getPasswordHash()));
        auth.setUser(user);
        authenticationRepository.save(auth);
        return true;
    }

    public boolean registerPreference(Preference pre,User user){
        if(pre == null || user == null) throw new CustomException("Empty Data",400);
        Optional<Authentication> existingPre = authenticationRepository.findById(pre.getPreferenceId());
        if(existingPre.isPresent()) throw new CustomException("User already exits",409);
        pre.setUser(user);
        preferenceRepository.save(pre);
        return true;
    }
}
