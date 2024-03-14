import { AutoCrud } from "@hilla/react-crud";
import { UserEndpoint } from "Frontend/generated/endpoints";
import UserModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserModel";

export default function UserCrud() {
  return (
    <AutoCrud
      service={UserEndpoint}
      model={UserModel}
      gridProps={{
        visibleColumns: [
          "id",
          "nickName",
          "description",
          "birthday",
          "otherNames",
          "wxOpenid",
        ],
      }}
      formProps={{
          visibleFields:[
          "nickName",
          "description",
          "birthday",
          "otherNames",
          ]
      }}
    />
  );
}
