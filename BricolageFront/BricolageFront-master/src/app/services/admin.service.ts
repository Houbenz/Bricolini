import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from '../models/User';
import {HOST} from './constants';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) {

  }

  searchUsers(query: string) {
    return this.http.get<User[]>(HOST + "/users/" + query);
  }

  getAllUsers() {
    return this.http.get<User[]>(HOST + "/users");
  }

  blockUser(id: number) {
    return this.http.delete(HOST + "/users/block/" + id);
  }
}
