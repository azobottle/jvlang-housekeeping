import { AutoGrid } from '@hilla/react-crud';
import { RoleService } from 'Frontend/generated/endpoints';
import RoleModel from 'Frontend/generated/com/example/application/persistence/document/RoleModel';

export default function RoleGrid() {
    return <AutoGrid service={RoleService} model={RoleModel} visibleColumns={['name','description']}/>;
}