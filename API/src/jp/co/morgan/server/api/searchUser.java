package jp.co.morgan.server.api;

import java.util.List;
import java.util.ArrayList;

import jp.co.morgan.server.dto.UserDto;

public class searchUser {
    public static void main(String[] args) {
        UserDto newUser = new UserDto();
        List<UserDto> newUserList = new ArrayList<UserDto>(); 
        
        for (int i = 0; i < 50; i++) {
            newUser.setUserName("test" + i);
            newUser.setEMail("test" + i + "@example.com");

            newUserList.add(newUser);
        }

        int size = newUserList.size();

        for (int i = 0; i < size; i++) {
            System.out.println(newUserList.get(i));
        }
    }
}
