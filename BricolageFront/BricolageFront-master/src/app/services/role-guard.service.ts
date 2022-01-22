import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {AuthenticationService} from "./authentication.service";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable()
export class RoleGuardService implements CanActivate {
  constructor(public auth: AuthenticationService,
              private jwtHelper: JwtHelperService,
              public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    // @ts-ignore
    const currentUser: any = JSON.parse(localStorage.getItem('authenticatedUser'));
    const tokenPayload = this.jwtHelper.decodeToken(currentUser.token);
    if (
      !this.auth.isAuthenticated() ||
      tokenPayload.role !== expectedRole
    ) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
