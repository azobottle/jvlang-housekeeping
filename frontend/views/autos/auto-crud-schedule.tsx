import { AutoCrud } from "@hilla/react-crud";
import { ScheduleEndpoint } from "Frontend/generated/endpoints";
import ScheduleModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/ScheduleModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";

export default function ScheduleCrud() {
  return (
    <AutoCrud<ScheduleModel>
      service={ScheduleEndpoint}
      model={ScheduleModel}
      gridProps={{
        visibleColumns: ["shifuName", "date", "availableMor","availableAft"],
        customColumns: [
          <GridColumn<ScheduleModel>
            key="shifuName"
            renderer={(obj) => {
              return (
                <span>{String(obj.item.shifu?.nickName ?? "师傅不存在")}</span>
              );
            }}
            header="师傅名"
            autoWidth
          />,
        ],
      }}
      formProps={{
          visibleFields:["shifuId", "date", "availableMor","availableAft"]
      }}
    />
  );
}
