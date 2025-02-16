package dev.richar.auth.models.services;

import dev.richar.auth.models.dao.OurUsersDao;
import dev.richar.auth.models.dto.ReqRes;
import dev.richar.auth.models.entity.OurUsers;
import dev.richar.auth.models.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersManagementService {


    @Autowired
    private OurUsersDao usersRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        var email = usersRepo.findByEmail(registrationRequest.getEmail());

        try {
            OurUsers ourUser = new OurUsers();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setCity(registrationRequest.getCity());
            //ERole rolEnum = ERole.valueOf(registrationRequest.getRole());
            ourUser.setRoles(Collections.singleton(ERole.ADMIN));
            ourUser.setName(registrationRequest.getName());

            if (email.isPresent() && email.get().getEmail().equals(registrationRequest.getEmail())) {
                resp.setStatusCode(400);
                resp.setMessage("User Already Exists");
                return resp;
            } else {
                ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            }

            OurUsers ourUsersResult = usersRepo.save(ourUser);
            if (ourUsersResult.getId()>0) {
                String rolesString = ourUsersResult.getRoles()
                        .stream()
                        .map(ERole::name)
                        .collect(Collectors.joining(", "));
                resp.setRole(Collections.singletonList(rolesString));
                resp.setOurUsers((ourUsersResult));
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            String rolesString = user.getRoles().stream()
                    .map(ERole::name)
                    .collect(Collectors.joining(", "));
            response.setRole(Collections.singletonList(rolesString));
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, ReqRes updateRequest) {
        ReqRes resp = new ReqRes();

        try {
            // 1. Buscar usuario por ID
            Optional<OurUsers> userOptional = usersRepo.findById(userId);

            if (userOptional.isEmpty()) {
                resp.setStatusCode(404);
                resp.setMessage("Usuario no encontrado");
                return resp;
            }

            OurUsers ourUser = userOptional.get();

            // 2. Actualizar campos básicos
            ourUser.setEmail(updateRequest.getEmail());
            ourUser.setName(updateRequest.getName());
            ourUser.setCity(updateRequest.getCity());

            // 3. Actualizar roles (si se proporcionan)
            if (updateRequest.getRole() != null && !updateRequest.getRole().isEmpty()) {
                try {
                    Set<ERole> newRoles = updateRequest.getRole()
                            .stream()
                            .map(String::trim)
                            .map(ERole::valueOf)
                            .collect(Collectors.toSet());
                    ourUser.setRoles(newRoles);
                } catch (IllegalArgumentException e) {
                    resp.setStatusCode(400);
                    resp.setMessage("Rol inválido: " + e.getMessage());
                    return resp;
                }
            }

            // 4. Actualizar contraseña (si se proporciona)
            if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                ourUser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            }

            // 5. Guardar cambios
            OurUsers updatedUser = usersRepo.save(ourUser);

            // 6. Preparar respuesta
            String rolesString = updatedUser.getRoles().stream()
                    .map(ERole::name)
                    .collect(Collectors.joining(", "));

            resp.setStatusCode(200);
            resp.setMessage("Usuario actualizado exitosamente");
            resp.setRole(Collections.singletonList(rolesString));
            resp.setOurUsers(updatedUser);

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error interno: " + e.getMessage());
        }

        return resp;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

}
