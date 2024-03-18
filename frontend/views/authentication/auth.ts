import { configureAuth } from "@hilla/react-auth";

// Configure auth to use `UserInfoService.getUserInfo`
const auth = configureAuth(async () => ({}), {
  getRoles: (userInfo) => [],
});

// Export auth provider and useAuth hook, which are automatically
// typed to the result of `UserInfoService.getUserInfo`
export const useAuth = auth.useAuth;
export const AuthProvider = auth.AuthProvider;
