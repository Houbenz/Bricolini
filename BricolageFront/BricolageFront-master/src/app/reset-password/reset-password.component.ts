import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthenticationService} from "../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isLoading: boolean = false;
  errorMessage: string | undefined;

  constructor(public auth: AuthenticationService, public router: Router) {
  }

  ngOnInit(): void {
  }

  onResetPassword(resetPasswordForm: NgForm) {
    this.isLoading = true;
    this.auth.resetPassword(resetPasswordForm.value).subscribe(res => {
    }, (err) => {
      this.errorMessage = err.error.message;
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
      this.router.navigate(['/']);
    });
    resetPasswordForm.reset();
  }
}
