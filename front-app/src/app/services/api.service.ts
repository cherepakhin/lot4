import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {Observable} from 'rxjs';
import {Message} from '../interfaces/message.interface';
import {VersionsTableData} from '../interfaces/table.interface';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  getVersions(): Observable<Array<VersionsTableData>> {
    return this.http.get<Array<VersionsTableData>>(`${environment.API_URL}/statistic/application`);
  }

  getMessages(phoneNumber: string): Observable<Array<Message>> {
    return this.http.get<Array<Message>>(
      `${environment.API_URL}/statistic/messages?phone=${phoneNumber}`
    );
  }
}
