import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ADMINISTRATEUR, BRICOLEUR, CLIENT, HOST} from "./constants";
import {JwtHelperService} from "@auth0/angular-jwt";
import {Router} from "@angular/router";
import {Role} from "../models/Role";
import {User} from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public currentUser: any;
  public showLogin: any = true;
  public showRegister: any;
  public showResetPassword : any;

  constructor(private http: HttpClient,
              private router: Router,
              public jwtHelper: JwtHelperService) {
  }

  login(loginData: FormData) {
    return this.http.post(HOST + "/authenticate", loginData, {observe: 'response'});
  }

  findByUsername(username: string) {
    return this.http.get(HOST + "/users/userByUsername?username=" + username);
  }

  register(registerData: User) {
    return this.http.post(HOST + "/users", registerData);
  }

  resetPassword(resetPasswordData: FormData) {
    return this.http.post(HOST + "/users/resetPassword", resetPasswordData);
  }

  getRoles() {
    return this.http.get<Role[]>(HOST + "/roles");
  }

  isAuthenticated() {
    // @ts-ignore
    const token: any = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  isUser() {
    // @ts-ignore
    const token: any = this.jwtHelper.decodeToken(localStorage.getItem('token'));
    let isUser = false;
    // @ts-ignore
    token?.roles?.forEach((role: any) => {
      if (role.authority == 'CLIENT') isUser = true;
    })
    return isUser;
  }

  isBricoleur() {
    // @ts-ignore
    const token: any = this.jwtHelper.decodeToken(localStorage.getItem('token'));
    let isBricoleur = false;
    // @ts-ignore
    token?.roles?.forEach((role: any) => {
      if (role.authority == BRICOLEUR) isBricoleur = true;
    })
    return isBricoleur;
  }

  isAdmin() {
    //@ts-ignore
    const token: any = this.jwtHelper.decodeToken(localStorage.getItem('token'));
    let isAdmin = false;
    //@ts-ignore
    token?.roles?.forEach((role: any) => {
      if (role.authority == ADMINISTRATEUR) isAdmin = true;
    });
    return isAdmin;
  }

  public getToken(): string {
    // @ts-ignore
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login'])
  }

  getUser() {
    return this.http.get(HOST + "/users/self");
  }

  updateUser(user: User) {
    return this.http.post(HOST + "/users/self", user);
  }

  deleteUser() {
    return this.http.delete(HOST + "/users/self");
  }

}
