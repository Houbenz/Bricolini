import {Component, OnInit} from '@angular/core';
import {AnnonceService} from "../../services/annonce.service";
import {Annonce} from "../../models/Annonce";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  title: any;
  subtitle: any;
  content: any;
  public errorMessage: any;
  public annonces: Annonce[] | undefined;

  constructor(public annonceService: AnnonceService) {
  }

  ngOnInit(): void {
    this.getAnnonces();
  }

  getAnnonces() {
    this.annonceService.isLoading = true;
    this.annonceService.getAnnonces().subscribe(
      data => {
        if (data.length == 0) {
          this.errorMessage = "Rien à afficher";
        }
        this.annonces = data;
      }, () => {

      }, () => {
        this.annonceService.isLoading = false;
      }
    )
  }
}
