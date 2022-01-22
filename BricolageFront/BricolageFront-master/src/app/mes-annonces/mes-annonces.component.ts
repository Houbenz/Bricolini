import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Annonce} from "../models/Annonce";
import {AnnonceService} from "../services/annonce.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DIALOG_ERROR} from "../models/TypeDialog";
import {DialogComponent} from "../dialog/dialog.component";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from '../services/authentication.service';

@Component({
  selector: 'app-mes-annonces',
  templateUrl: './mes-annonces.component.html',
  styleUrls: ['./mes-annonces.component.css']
})
export class MesAnnoncesComponent implements AfterViewInit, OnInit {
  constructor(public annonceService: AnnonceService,
              private router: Router,
              private route: ActivatedRoute,
              public authService: AuthenticationService,
              public matDialog: MatDialog) {
  }

  displayedColumns: string[] = ['id', 'titre', 'budgetClient', 'date', 'description', 'details', 'adresse','etat', 'seeDetails', 'edit', 'delete'];
  annonces: Annonce[] = [];
  dataSource = new MatTableDataSource<Annonce>(this.annonces);
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
    if (this.authService.isAdmin())
      this.getAllAnnonces();
    else
      this.getAnnoncesByUser(!this.authService.isBricoleur());
  }


  getAllAnnonces() {

    this.annonceService.isLoading = true;
    this.annonceService.getAnnonces().subscribe(
      data => {
        if (data.length == 0) {
          this.showMessage = true;
          this.errorMessage = "Rien à afficher";
        }
        this.dataSource.data = data;
      }, () => {

      }, () => {
        this.annonceService.isLoading = false;
      }
    )
  }


  getAnnoncesByUser(client: boolean) {//client ou bricoleur
    this.annonceService.isLoading = true;

    let observable;

    if (client) {
      observable = this.annonceService.getAnnoncesByClient();
    } else {
      observable = this.annonceService.getAnnoncesByBricoleur();
    }

    observable.subscribe(
      data => {
        if (data.length == 0) {
          this.showMessage = true;
          this.errorMessage = "Rien à afficher";
        }
        this.dataSource.data = data;
      }, () => {

      }, () => {
        this.annonceService.isLoading = false;
      }
    )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSeeDetails(id: number) {
    this.router.navigate([id], {relativeTo: this.route});
  }

  onEditAnnonce(id: number) {
    this.router.navigate(['modifierAnnonce', id]);
  }

  onDeleteAnnonce(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "SUPPRESSION DE L'ANNONCE",
      message: "Etes-vous sur de vouloir supprimer cette l'annonce " + id,
      type: DIALOG_ERROR
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig)
    modalDialog.afterClosed().subscribe(result => {
      if (result) {
        this.annonceService.isLoading = true;
        this.annonceService.deleteAnnonce(id).subscribe(data => {
        }, () => {
        }, () => {
          this.getAnnoncesByUser(!this.authService.isBricoleur());
        });
      }
    });
  }

}

const ELEMENT_DATA: Annonce[] = [];
