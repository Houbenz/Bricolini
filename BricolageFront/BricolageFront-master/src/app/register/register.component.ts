import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../services/authentication.service";
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from "@angular/router";
import {Domaine} from "../models/Domaine";
import {DomaineService} from "../services/domaine.service";
import {Role} from "../models/Role";
import {User} from '../models/User';
import {BRICOLEUR} from "../services/constants";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(public authservice: AuthenticationService,
              private domaineService: DomaineService,
              private jwtHelper: JwtHelperService,
              private router: Router) {
  }

  user: User = new User();
  domaines: Domaine[] | undefined;
  roles: Role[] | undefined;
  showBricoleurFields: boolean = false;
  isLoading: boolean = false;
  responseMessage: string | undefined;
  isError: boolean | undefined;

  ngOnInit(): void {

    this.getDomains();
    this.getRoles();

    this.user.domaine = undefined;
    this.user.premium = false;
    this.user.role = "CLIENT";
  }


  getDomains() {
    this.isLoading = true;
    this.domaineService.getDomaines().subscribe(
      (data) => {
        this.domaines = data;
      }, () => {
        // to handle error
      }, () => {
        this.isLoading = false;
      });
  }

  getRoles() {
    this.isLoading = true;
    this.authservice.getRoles().subscribe(
      (roles) => {
        if (!this.authservice.isAdmin() || !this.authservice.isAuthenticated()){
          this.roles = roles.filter(role => role.role != "ADMINISTRATEUR");
        }else{
          this.roles = roles;
        }
      }, () => {
      },
      () => {
        this.isLoading = false;
      });
  }

  onRoleChange() {
    if (this.user.role == "BRICOLEUR") {
      this.showBricoleurFields = true;
      this.user.premium = false;
    } else {
      this.user.domaine = undefined;
      this.showBricoleurFields = false;
    }
  }


  onRegister() {
    this.authservice.register(this.user).subscribe(response => {
        this.isError = false;
        this.responseMessage = "Le compte a été créé avec succès";
      },
      (error) => {
        this.isError = true;
        this.responseMessage = error.error.message;
      },()=>{
        if (this.authservice.isAdmin()){
          this.router.navigate(['/']);
        }
        this.authservice.showLogin = true;
        this.authservice.showResetPassword = false;
        this.authservice.showRegister = false;
      },
    );
  }

}
