import { Annonce } from "./Annonce";
import { User } from "./User";

export interface Signalement {
  idAnnonce: number;
  idUser: number;
  message: string;
  raison: string;
  user: User;
  annonce: Annonce;
}
