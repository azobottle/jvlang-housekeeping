import {AutoCrud} from "@hilla/react-crud";
import {RelaUserRoleViewEndPoint} from "Frontend/generated/endpoints";
import View1Model from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RelationUserRole/View1Model"

export default function RelationUserRoleCrud() {
    return (
        <AutoCrud
            service={RelaUserRoleViewEndPoint}
            model={View1Model}
            formProps={{
                visibleFields: ['userNickName', 'roleName']
            }}
        />
    );
}
