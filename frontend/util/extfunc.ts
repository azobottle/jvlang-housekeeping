import { NumberModel, ObjectModel } from "@hilla/form";
import UserPubInfo from "../generated/com/jvlang/housekeeping/pojo/vo/UserPubInfo";

export function userPubInfo<
  O,
  K extends keyof O,
  T extends Record<string, UserPubInfo>
>(
  that: { [P in K]?: number | undefined } & {
    userPubInfos: T;
  },
  key: K
): UserPubInfo | undefined {
  const k = that[key];
  if (k === null || k === undefined) {
    return undefined;
  }
  return that.userPubInfos[k.toString()];
}
