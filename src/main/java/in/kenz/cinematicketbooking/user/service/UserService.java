package in.kenz.cinematicketbooking.user.service;

import in.kenz.cinematicketbooking.user.dto.ChangePasswordDTO;
import in.kenz.cinematicketbooking.user.dto.UserDTO;

public interface UserService {

    UserDTO findByUsername(String username);

    void updateProfile(String username, UserDTO userDTO);

    void changePassword(String username, ChangePasswordDTO dto);
}
