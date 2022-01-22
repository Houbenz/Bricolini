import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../services/authentication.service";
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from "@angular/router";
import {DomaineService} from "../services/domaine.service";
import {ADMINISTRATEUR, BRICOLEUR, CLIENT} from "../services/constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoading: boolean = false;
  errorMessage: String |undefined;

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('',
      [Validators.required, Validators.minLength(3)]),
  });

  constructor(public authservice: AuthenticationService,
              private domaineService: DomaineService,
              private jwtHelper: JwtHelperService,
              private router: Router) {
  }

  ngOnInit(): void {

  }

  onShowRegister() {
    this.authservice.showRegister = true;
    this.authservice.showLogin = false;
    this.authservice.showResetPassword = false;
  }


  onShowLogin() {
    this.authservice.showLogin = true;
    this.authservice.showRegister = false;
    this.authservice.showResetPassword = false;
  }

  onLogin() {
    this.authservice.login(this.loginForm.value).subscribe(
      resp => {
        // @ts-ignore
        let token: any = resp.headers.get('authorization');
        localStorage.setItem("token", token);
        let userData = this.jwtHelper.decodeToken(token);
        this.authservice.findByUsername(userData.sub).subscribe((currentUser: any) => {
          localStorage.setItem("currentUserId", currentUser.id);
        }, (err) => {
        }, () => {
          userData.roles.forEach((role: any) => {
            if (role.authority == BRICOLEUR) this.router.navigate(['/annonces']);
            if (role.authority == CLIENT || role.authority == ADMINISTRATEUR) this.router.navigate(['/']);
          })
        })
      },()=>{
        this.errorMessage = "Email et/ou mot de passe incorrect(s)"
      },()=>{

      });
    this.loginForm.reset();
  }

  onShowResetPassword() {
    this.authservice.showResetPassword = true;
    this.authservice.showLogin = false;
    this.authservice.showRegister = false;
  }
}
