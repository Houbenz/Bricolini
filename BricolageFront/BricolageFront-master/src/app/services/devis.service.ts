import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HOST} from "./constants";
import {Devis} from '../models/Devis';

@Injectable({
  providedIn: 'root'
})
export class DevisService {
  SERVER_ADRESSE = HOST;
  isLoading = false;

  constructor(private http: HttpClient) {
  }

  addDevis(idProposition: number) {
    return this.http.post<Devis>(this.SERVER_ADRESSE + "/devis", idProposition);
  }

  /*getPropositions() {
      return this.http.get<Proposition[]>(this.SERVER_ADRESSE + "/propositions");
    }
  getPropositionsByAnnonce(idAnnonce : number | undefined) {
      return this.http.get<Proposition[]>(this.SERVER_ADRESSE + "/propositions/annonce/"+idAnnonce);
    }
  getCurrentUser() {
      return this.http.get<User[]>(this.SERVER_ADRESSE + "/propositions/currentUser");
    }
  deleteProposition(id : number){
    return this.http.delete(this.SERVER_ADRESSE + "/propositions/"+id)
  }*/
}
