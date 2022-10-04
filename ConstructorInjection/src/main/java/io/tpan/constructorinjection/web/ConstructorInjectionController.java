package io.tpan.constructorinjection.web;

import io.tpan.constructorinjection.dto.UserDataDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorInjectionController {
    final
    UserDataDto userDataDto;

    public ConstructorInjectionController(UserDataDto userDataDto) {
        this.userDataDto = userDataDto;
    }

    @PostMapping("/saveUserNameCi")
    public void saveUserName(@RequestBody String userName){
        userDataDto.setUserName(userName);
    }

    @PostMapping("/saveUserNameReplaceInstanceCi")
    public void saveUserNameReplaceInstance(@RequestBody String userName){
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUserName(userName);
        //this.userDataDto = userDataDto;
    }

    @GetMapping("/getUserNameCi")
    public String getUserName(){
        return userDataDto.getUserName();
    }
}
