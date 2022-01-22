import {User} from "./User";
import {Annonce} from "./Annonce";

export interface Proposition {
  id: number;
  message: string;
  price: string;
  date:string;
  idAnnonce: number;
  idProfessionnel: number;
  devis: boolean;
  bricoleur: User;
  annonce: Annonce;
}
