import { AutoGrid } from "@hilla/react-crud";
import { UserEndpoint } from "Frontend/generated/endpoints";
import UserModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserModel";

export default function UserGrid() {
  return <AutoGrid service={UserEndpoint} model={UserModel}
                   visibleColumns={['name', 'phoneNumber']}
  />;
}
