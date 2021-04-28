import { Component } from '@angular/core';
import {ApiService} from '../../services/api.service';
import { VersionsTableData } from '../../interfaces/table.interface';

@Component({
  selector: 'app-version-list-page',
  templateUrl: './version-list-page.component.html',
  styleUrls: ['./version-list-page.component.scss']
})
export class VersionListPageComponent {
  tableData!: Array<VersionsTableData>;
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


  isLoading = true;
  isDataLoaded = false;
  isLoadingError = false;


  constructor(public apiService: ApiService) {
    this.apiService.getVersions().subscribe(
      (res: Array<VersionsTableData>) => {
      this.tableData = res;

      this.isDataLoaded = true;
      this.isLoading = false;
    },
    () => {
        this.isLoadingError = true;
        this.isLoading = false;
    });
  }
}
