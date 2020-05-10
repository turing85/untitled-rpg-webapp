import { Component, Input, OnInit } from '@angular/core';

interface PathSegment {
  name: string;
  link?: string;
}
@Component({
  selector: 'rpg-breadcrumb',
  templateUrl: './breadcrumb.component.html'
})
export class BreadcrumbComponent implements OnInit {
  @Input()
  path: PathSegment[];
  constructor() { }

  ngOnInit(): void { }
}
