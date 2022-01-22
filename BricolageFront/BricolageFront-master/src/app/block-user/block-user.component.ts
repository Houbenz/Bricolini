import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../models/User';
import {AdminService} from '../services/admin.service';

@Component({
  selector: 'app-block-user',
  templateUrl: './block-user.component.html',
  styleUrls: ['./block-user.component.css']
})
export class BlockUserComponent implements OnInit {

  constructor(private adminService: AdminService,) {
  }

  users: User[] = [];

  message: string | undefined;

  blockForm = new FormGroup({
    queryControl: new FormControl('', [Validators.required]),
  });

  ngOnInit(): void {
  }


  onSearch() {

    if (this.blockForm.invalid) {


    } else {
      let username: string = this.blockForm.get('queryControl')?.value;

      this.adminService.searchUsers(username).subscribe(response => {
        this.users = response;
      });
    }
  }
}
