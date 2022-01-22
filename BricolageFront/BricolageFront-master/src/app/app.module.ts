import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoadingSpinnerComponent} from './loading-spinner/loading-spinner.component';
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DialogComponent} from './dialog/dialog.component';
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DemandeServiceComponent} from './demande-service/demande-service.component';
import {MatIconModule} from "@angular/material/icon";
import {MatBadgeModule} from "@angular/material/badge";
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatMenuModule} from '@angular/material/menu';
import {AdminHomeComponent} from './admin-home/admin-home.component';
import {AddDomaineComponent} from './add-domaine/add-domaine.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MesAnnoncesComponent} from './mes-annonces/mes-annonces.component';
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatInputModule} from '@angular/material/input';
import {AllDomainesComponent} from './all-domaines/all-domaines.component';
import {BlockUserComponent} from './block-user/block-user.component';
import {AddAvisComponent} from './add-avis/add-avis.component';
import {GetAvisComponent} from './add-avis/get-avis/get-avis.component';
import {AddPropositionComponent} from './add-propos/add-proposition.component';
import {AnnonceComponent} from './mes-annonces/annonce/annonce.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from '@angular/material/core';
import {JwtModule} from "@auth0/angular-jwt"
import {TokenInterceptor} from "./services/token.interceptor";
import {UnauthorizedComponent} from './unauthorized/unauthorized.component';
import {CardComponent} from './mes-annonces/card/card.component';
import {MatCardModule} from "@angular/material/card";
import {ListUsersComponent} from './block-user/list-users/list-users.component';
import {AddSignalementComponent} from './signalement/add-signalement/add-signalement.component';
import {ListPropositionComponent} from './list-propos/list-proposition.component';
import {GetSignalementComponent} from './signalement/get-signalement/get-signalement.component';
import {RegisterComponent} from './register/register.component';
import {PaiementComponent} from './paiement/paiement.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    LoadingSpinnerComponent,
    ResetPasswordComponent,
    MyProfileComponent,
    DialogComponent,
    DemandeServiceComponent,
    AdminHomeComponent,
    AddDomaineComponent,
    AddPropositionComponent,
    ListPropositionComponent,
    AllDomainesComponent,
    BlockUserComponent,
    MesAnnoncesComponent,
    AddAvisComponent,
    GetAvisComponent,
    AddSignalementComponent,
    GetSignalementComponent,
    AnnonceComponent,
    UnauthorizedComponent,
    CardComponent,
    ListUsersComponent,
    RegisterComponent,
    PaiementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatDialogModule,
    NgbModule,
    MatIconModule,
    MatBadgeModule,
    MatTooltipModule,
    MatMenuModule,
    HttpClientModule,
    MatTableModule,
    MatPaginatorModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    JwtModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('access_token')
      }
    }),
    MatCardModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
