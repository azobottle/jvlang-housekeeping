import { configureAuth } from "@hilla/react-auth";
import { get } from "Frontend/generated/UserEndpoint";
import User from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/User";
import Role0 from "Frontend/generated/com/jvlang/housekeeping/pojo/Role0";
import { jwtDecode, JwtPayload } from "jwt-decode";

interface OurJwtPayload extends JwtPayload {
  user_id: number;
}

// Configure auth to use `UserInfoService.getUserInfo`
const auth = configureAuth<User>(
  async () => {
    const auth_token = localStorage.getItem("auth_token");
    if (!auth_token) {
      return undefined;
    } else {
      return await get(jwtDecode<OurJwtPayload>(auth_token).user_id);
    }
  },
  {
    getRoles: (userInfo) =>
      userInfo.roles?.filter((it): it is Role0 => it != undefined) ?? [],
  }
);

// Export auth provider and useAuth hook, which are automatically
// typed to the result of `UserInfoService.getUserInfo`
export const useAuth = auth.useAuth;
export const AuthProvider = auth.AuthProvider;
