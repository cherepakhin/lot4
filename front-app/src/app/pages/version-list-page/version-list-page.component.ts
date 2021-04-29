import {Component} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {VersionsTableData} from '../../interfaces/table.interface';
import {ErrorsEnum} from '../../enums/errors.enum';

@Component({
  selector: 'app-version-list-page',
  templateUrl: './version-list-page.component.html',
  styleUrls: ['./version-list-page.component.scss']
})
export class VersionListPageComponent {
  tableData: Array<VersionsTableData>;
  columns = [
    {
      id: 'version',
      label: 'Version'
    },
    {
      id: 'registrationsCount',
      label: 'Number of registrations'
    },
    {
      id: 'phoneNumbersCount',
      label: 'Number of phone numbers'
    },
  ];

  errorText: string;
  isLoading = true;
  isDataLoaded = false;


  constructor(public apiService: ApiService) {
    this.apiService.getVersions().subscribe(
      (res: Array<VersionsTableData>) => {
      this.tableData = res;
      if (this.tableData && this.tableData.length === 0) {
          this.errorText = ErrorsEnum.NO_VERSIONS_ERROR;
        }
      this.isDataLoaded = true;
      this.isLoading = false;
    },
    () => {
        this.isLoading = false;
        this.errorText = ErrorsEnum.LOADING_ERROR;
    });
  }
}
