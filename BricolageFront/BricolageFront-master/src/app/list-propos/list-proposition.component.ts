import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DIALOG_ERROR} from "../models/TypeDialog";
import {DialogComponent} from "../dialog/dialog.component";
import {ActivatedRoute, Router} from "@angular/router";
import {PropositionService} from '../services/proposition.service';
import {Proposition} from '../models/Proposition';

@Component({
  selector: 'app-list-proposition',
  templateUrl: './list-proposition.component.html',
  styleUrls: ['./list-proposition.component.css']
})
export class ListPropositionComponent implements AfterViewInit, OnInit {
  constructor(public propoService: PropositionService,
              private router: Router,
              private route: ActivatedRoute,
              public matDialog: MatDialog) {
  }

  displayedColumns: string[] = ['idAnnonce', 'date_debut', 'prix_propose', 'prix_client', 'seeDetails'/* , 'edit' */, 'delete'];
  propositions: Proposition[] = [];
  dataSource = new MatTableDataSource<Proposition>(this.propositions);
  isloading: boolean = false;
  retrievedImage: string | undefined;
  showMessage: boolean | undefined;
  errorMessage: string | undefined;
  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  ngAfterViewInit() {
    // @ts-ignore
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.getMyPropositions();
  }

  getMyPropositions() {
    this.propoService.getPropositions().subscribe(
      data => {
        data.forEach(d=>{
        })
        if (data.length == 0) {
          this.showMessage = true;
          this.errorMessage = "Rien à afficher"
        }
        this.dataSource.data = data;
      }, () => {

      }, () => {
        this.propoService.isLoading = false;
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSeeDetails(id: number) {
    this.router.navigate(['/mesAnnonces/' + id]);
  }

  onEditProposition(id: number) {

  }

  onDeleteProposition(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "SUPPRESSION DE LA PROPOSITION",
      message: "Etes-vous sur de vouloir supprimer cette proposition " + id,
      type: DIALOG_ERROR
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig)
    modalDialog.afterClosed().subscribe(result => {
      if (result) {
        this.propoService.isLoading = true;
        this.propoService.deleteProposition(id).subscribe(data => {
        }, () => {
        }, () => {
          this.getMyPropositions();
        });
      }
    });
  }
}

const ELEMENT_DATA: Proposition[] = [];

