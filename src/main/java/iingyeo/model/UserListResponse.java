package iingyeo.model;

import iingyeo.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Taemyung on 2015-06-06.
 */
@Data
public class UserListResponse implements Serializable {

    private Integer totalPage;
    private Long totalCount;
    private Integer pageNum;
    private List<UserResponse> users;

    public UserListResponse() {
    }

    public UserListResponse(Integer totalPage, Long totalCount, Integer pageNum, List<User> users) {

        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageNum = pageNum;

        this.users = users.stream().map(UserResponse::new).collect(Collectors.toList());

    }

}
