import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";
import {DemandeServiceComponent} from "./demande-service/demande-service.component";
import {AdminHomeComponent} from './admin-home/admin-home.component';
import {AddDomaineComponent} from './add-domaine/add-domaine.component';
import {MesAnnoncesComponent} from "./mes-annonces/mes-annonces.component";
import {BlockUserComponent} from './block-user/block-user.component';
import {AddAvisComponent} from './add-avis/add-avis.component';
import {GetAvisComponent} from './add-avis/get-avis/get-avis.component';
import {AddSignalementComponent} from './signalement/add-signalement/add-signalement.component';
import {GetSignalementComponent} from './signalement/get-signalement/get-signalement.component';
import {AnnonceComponent} from "./mes-annonces/annonce/annonce.component";
import {ListPropositionComponent} from './list-propos/list-proposition.component';
import {AuthGuardService as AuthGuard} from './services/auth-guard.service';
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";
import {CardComponent} from "./mes-annonces/card/card.component";
import {RegisterComponent} from './register/register.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'monProfile', component: MyProfileComponent, canActivate: [AuthGuard]},
  {path: 'demanderService', component: DemandeServiceComponent, canActivate: [AuthGuard]},
  {path: 'mesAnnonces', component: MesAnnoncesComponent, canActivate: [AuthGuard]},
  {path: 'modifierAnnonce/:id', component: DemandeServiceComponent, canActivate: [AuthGuard]},
  {path: 'add_avis/:id', component: AddAvisComponent},
  {path: 'list_proposition', component: ListPropositionComponent},
  {path: 'add_signalement/:id', component: AddSignalementComponent},
  {path: 'get_signalement/:type/:id', component: GetSignalementComponent},
  {path: 'annonces', component: CardComponent},
  {path: 'get_avis/:type/:id', component: GetAvisComponent},
  {path: 'mesAnnonces/:id', component: AnnonceComponent},
  {
    path: 'login', component: LoginComponent, children: [
      {path: ':id/:name', component: LoginComponent}
    ],
  },
  {path: 'admin/home', component: AdminHomeComponent, canActivate: [AuthGuard]},
  {path: 'admin/add_domaine', component: AddDomaineComponent, canActivate: [AuthGuard]},
  {path: 'admin/block_user', component: BlockUserComponent, canActivate: [AuthGuard]},
  {path: 'admin/voir_signalements', component: GetSignalementComponent, canActivate: [AuthGuard]},
  {path: 'ajouter_moderateur', component: RegisterComponent, canActivate: [AuthGuard]},
  {path: 'signalements', component: GetSignalementComponent, canActivate: [AuthGuard]},
  {path: 'unauthorized', component: UnauthorizedComponent},
  {path: '**', redirectTo: ''}

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
