import { AutoCrud } from "@hilla/react-crud";
import { RelationShifuServiceEndpoint } from "Frontend/generated/endpoints";
import RelationShifuServiceModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RelationShifuServiceModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";
import { Upload } from "@hilla/react-components/Upload";
import { userPubInfo } from "Frontend/util/extfunc";
import RelationShifuService from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RelationShifuService";

export default function RelationShifuServiceEndpointCrud() {
  const uploadHeader = {
    type: "rela_shifu_service",
    Authorization: localStorage.getItem("auth_token"),
  };
  return (
    <div className="p-m">
      <AutoCrud<RelationShifuServiceModel>
        service={RelationShifuServiceEndpoint}
        model={RelationShifuServiceModel}
        gridProps={{
          visibleColumns: [
            "id",
            "createTime",
            "modifyTime",
            "shifuId",
            "serviceId",
            "shifuName",
            "serviceName",
          ],
          customColumns: [
            <GridColumn<RelationShifuServiceModel>
              key="shifuName"
              renderer={(obj) => {
                return (
                  <span>
                    {String(
                      userPubInfo(obj.item.valueOf(), "shifuId")?.displayName ??
                        "师傅不存在"
                    )}
                  </span>
                );
              }}
              header="师傅名"
              autoWidth
            />,
            <GridColumn<RelationShifuServiceModel>
              key="serviceName"
              renderer={(obj) => {
                return <span>{String("TODO")}</span>;
              }}
              header="服务名"
              autoWidth
            />,
          ],
        }}
        formProps={{
          visibleFields: ["shifuId", "serviceId"],
        }}
      />
      <Upload target="/api/file/upload" headers={uploadHeader} />
    </div>
  );
}
