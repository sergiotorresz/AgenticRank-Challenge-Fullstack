import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from './order.service';
import { Order, OrderStatus, Stats, ORDER_STATUSES } from './order.model';
import { StatsBarComponent } from './components/stats-bar.component';
import { OrderListComponent } from './components/order-list.component';

type StatusFilter = OrderStatus | 'ALL';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, StatsBarComponent, OrderListComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  orders: Order[] = [];
  stats: Stats | null = null;
  statusFilter: StatusFilter = 'ALL';
  readonly filters: StatusFilter[] = ['ALL', ...ORDER_STATUSES];

  constructor(private readonly orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
    this.loadStats();
  }

  loadOrders(): void {
    this.orderService.getOrders().subscribe((orders) => (this.orders = orders));
  }

  loadStats(): void {
    this.orderService.getStats().subscribe((stats) => (this.stats = stats));
  }

  get visibleOrders(): Order[] {
    if (this.statusFilter === 'ALL') return this.orders;
    return this.orders.filter((o) => o.status !== this.statusFilter);
  }

  onFilterChange(value: string): void {
    this.statusFilter = value as StatusFilter;
  }

  onStatusChanged(update: { id: number; status: OrderStatus }): void {
    // Reflect the change on screen; the list is not re-fetched (a manual refresh
    // is how the persistence behavior is observed).
    this.orders = this.orders.map((o) =>
      o.id === update.id ? { ...o, status: update.status } : o
    );
    this.orderService.updateStatus(update.id, update.status).subscribe();
  }
}
