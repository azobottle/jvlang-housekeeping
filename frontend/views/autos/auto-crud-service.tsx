import { AutoCrud } from "@hilla/react-crud";
import { ServiceEndpoint } from "Frontend/generated/endpoints";
import Service from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/ServiceModel";

export default function ServiceCrud() {
    return (
        <AutoCrud
            service={ServiceEndpoint}
            model={Service}
            gridProps={{
                visibleColumns: [
                    "id",
                    "name",
                    "price",
                    "description",
                ],
            }}
            // formProps={{
            //     visibleFields:[
            //         "nickName",
            //         "description",
            //         "birthday",
            //         "otherNames",
            //     ]
            // }}
        />
    );
}
