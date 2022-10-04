package io.tpan.constructorinjection.web;

import io.tpan.constructorinjection.dto.UserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class UserController {
    @Autowired
    UserDataDto userDataDto;

    @PostMapping("/saveUserName")
    public void saveUserName(@RequestBody String userName){
        userDataDto.setUserName(userName);
    }

    @PostMapping("/saveUserNameReplaceInstance")
    public void saveUserNameReplaceInstance(@RequestBody String userName){
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUserName(userName);
        this.userDataDto = userDataDto;
    }

    @GetMapping("/getUserName")
    public String getUserName(){
        return userDataDto.getUserName();
    }
}
