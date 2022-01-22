import {Injectable} from '@angular/core';
import {Proposition} from "../models/Proposition";
import {User} from "../models/User";
import {HttpClient} from "@angular/common/http";
import {HOST} from "./constants";

@Injectable({
  providedIn: 'root'
})
export class PropositionService {
  SERVER_ADRESSE = HOST;
  isLoading = false;
  propositions: Proposition[] | undefined;

  constructor(private http: HttpClient) {
  }

  addProposition(proposition: Proposition) {
    return this.http.post<Proposition>(this.SERVER_ADRESSE + "/propositions", proposition);

  }

  getPropositions() {
    return this.http.get<Proposition[]>(this.SERVER_ADRESSE + "/propositions");
  }

  getPropositionsByAnnonce(idAnnonce: number | undefined) {
    return this.http.get<Proposition[]>(this.SERVER_ADRESSE + "/propositions/annonce/" + idAnnonce);
  }

  getCurrentUser() {
    return this.http.get<User[]>(this.SERVER_ADRESSE + "/propositions/currentUser");
  }

  deleteProposition(id: number) {
    return this.http.delete(this.SERVER_ADRESSE + "/propositions/" + id)
  }
}
