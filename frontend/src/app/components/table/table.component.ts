import { Component, OnInit, ViewChild, Input, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { VersionsTableData, Column } from '../../interfaces/table.interface';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit, AfterViewInit {
  dataSource!: MatTableDataSource<VersionsTableData>;

  @Input() tableData: Array<VersionsTableData> = [];
  @Input() columns: Array<Column> = [];
  baseColumnsIds: Array<string> = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  get displayedColumns(): Array<string> {
    return [...this.baseColumnsIds];
  }

  ngOnInit(): void {
    this.columns.forEach((column) => {
      this.baseColumnsIds.push(column.id);
    });
    this.dataSource = new MatTableDataSource(this.tableData);
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
}
