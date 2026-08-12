import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order, OrderStatus, Stats } from './order.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  constructor(private readonly http: HttpClient) {}

  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/orders');
  }

  updateStatus(id: number, status: OrderStatus): Observable<Order> {
    return this.http.put<Order>(`/api/orders/${id}/status`, { status });
  }

  getStats(): Observable<Stats> {
    return this.http.get<Stats>('/api/stats');
  }
}
