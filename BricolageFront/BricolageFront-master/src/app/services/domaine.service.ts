import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Domaine} from '../models/Domaine';
import {ResponseString} from '../models/ResponseString';
import {HOST} from "./constants";

@Injectable({
  providedIn: 'root'
})
export class DomaineService {


  constructor(private http: HttpClient) {
  }

  SERVER_ADDRESS = HOST;
  MOCK_SERVER = "https://c80915be-87e8-4435-89d4-228c5f6f90d2.mock.pstmn.io";

  getDomaines() {
    return this.http.get<Domaine[]>(this.SERVER_ADDRESS + "/domaines");
  }

  addDomaine(domaine: Domaine) {
    return this.http.post<ResponseString>(this.SERVER_ADDRESS + "/domaines", domaine);
  }

  deleteDomaine(id: number) {
    return this.http.delete(this.SERVER_ADDRESS + "/domaines/" + id, {
      headers: {
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': '*'
      },
    })
  }
}
