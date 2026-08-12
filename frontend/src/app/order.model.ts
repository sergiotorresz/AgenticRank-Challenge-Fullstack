export type OrderStatus = 'RECEIVED' | 'PREPARING' | 'READY' | 'DELIVERED' | 'CANCELLED';

export interface Order {
  id: number;
  reference: string;
  customer: string;
  amountCents: number;
  status: OrderStatus;
  createdAt: string;
}

export interface Stats {
  revenueTodayCents: number;
  activeCount: number;
}

export const ORDER_STATUSES: OrderStatus[] = [
  'RECEIVED',
  'PREPARING',
  'READY',
  'DELIVERED',
  'CANCELLED'
];
