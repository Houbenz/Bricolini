import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../services/authentication.service";
import {NotificationService} from "../services/notification.service";
import {Notification} from "../models/Notification";
import {Router} from "@angular/router";
import {AnnonceService} from "../services/annonce.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {PaiementComponent} from "../paiement/paiement.component";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  public matBadge: number | undefined;
  public notifications: Notification[] | undefined;

  constructor(public auth: AuthenticationService,
              private router: Router,
              private annonceService: AnnonceService,
              public matDialog: MatDialog,
              private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.notificationService.getNotifications().subscribe(data => {
      if (data.length != 0) this.matBadge = data.length;
      this.notifications = data;
    })
  }

  onLogout() {
    this.auth.logout();
  }

  navigateToAnnonce(idAnnonce: number, idNotif: number) {
    this.notificationService.viewNotification(idNotif).subscribe(data => {
    }, () => {
    }, () => {
      this.router.navigate(['mesAnnonces', idAnnonce])
    });

  }

  onGetPremium() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = "42%";
    dialogConfig.width = "32%";
    dialogConfig.disableClose = false;
    dialogConfig.id = "dialog-get-premium";
    const modalDialog = this.matDialog.open(PaiementComponent, dialogConfig)
  }
}
