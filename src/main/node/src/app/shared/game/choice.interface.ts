export interface Choice {
  id?: string;
  targetChoice?: string;
  card?: bigint;
  yes?: boolean;
  cardOptions?: [];
  required?: boolean;
  expectedAnswerType?: string;
  options?: string[];
  message?: string;
  done?: boolean;
}
