import java.util.Scanner;
import java.util.Locale;

class Funcionario {
    String Nome;
    int Departamento;
    int HorasTrabalhadas;
}

public class Main {
    static float calcularSalarioBase(int Departamento, int HorasTrabalhadas) {
        float ValorHora = (Departamento == 1) ? 22.0f : 12.0f;
        return HorasTrabalhadas * ValorHora;
    }

    static float calcularHoraExtra(int Departamento, int HorasTrabalhadas) {
        float ValorHora = (Departamento == 1) ? 22.0f : 12.0f;
        float HorasAdicionais = (HorasTrabalhadas > 40) ? (HorasTrabalhadas - 40) : 0;
        return ValorHora * 2 * HorasAdicionais;
    }

    static float calcularInsalubridade(int Departamento, float salarioBase) {
        return (Departamento == 2) ? (0.15f * salarioBase) : 0;
    }

    static float calcularBonus(float salarioBase, int Departamento, int HorasTrabalhadas) {
        float bonus = 0;
        if (Departamento == 1) {
            if (HorasTrabalhadas > 20 && HorasTrabalhadas <= 30) {
                bonus += 0.03f * salarioBase;
            } else if (HorasTrabalhadas > 30 && HorasTrabalhadas <= 40) {
                bonus += 0.05f * salarioBase;
            } else if (HorasTrabalhadas > 40) {
                bonus += 0.1f * salarioBase;
            }
        }
        return bonus;
    }

    static float calcularINSS(float SalarioBruto) {
        return 0.07f * SalarioBruto;
    }

    static float calcularIR(float SalarioBruto) {
        return 0.12f * SalarioBruto;
    }

    static float calcularSalarioLiquido(float SalarioBruto) {
        return SalarioBruto - calcularINSS(SalarioBruto) - calcularIR(SalarioBruto) - 20.0f;
    }

    static void printSeparatorLine(int width) {
        System.out.println("-".repeat(width));
    }

    static void printTableRow(String coluna1, float coluna2, float coluna3, float coluna4, float coluna5, float coluna6, float coluna7, float coluna8) {
        System.out.printf("%-15s | %15.2f | %15.2f | %15.2f | %15.2f | %15.2f | %15.2f | %15.2f%n", coluna1, coluna2, coluna3, coluna4, coluna5, coluna6, coluna7, coluna8);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));
        Scanner scanner = new Scanner(System.in);
        final int NumFuncionarios = 5;
        Funcionario[] funcionarios = new Funcionario[NumFuncionarios];

        for (int i = 0; i < NumFuncionarios; ++i) {
            funcionarios[i] = new Funcionario();
            System.out.print("Informe o nome do funcionário " + (i+1) + ": ");
            funcionarios[i].Nome = scanner.nextLine();
            if (funcionarios[i].Nome.length() > 20) {
                funcionarios[i].Nome = funcionarios[i].Nome.substring(0, 20);
            }

            boolean departamentoValido = false;
            while (!departamentoValido) {
                System.out.print("Informe o departamento do funcionário " + (i+1) + " (1-Administrativo / 2-Produção): ");
                funcionarios[i].Departamento = scanner.nextInt();
                if (funcionarios[i].Departamento == 1 || funcionarios[i].Departamento == 2) {
                    departamentoValido = true;
                } else {
                    System.out.println("Opção inválida. Insira 1 para Administrativo ou 2 para Produção.");
                    scanner.nextLine();
                }
            }

            System.out.print("Informe o número de horas trabalhadas pelo funcionário " + (i+1) + " no mês: ");
            funcionarios[i].HorasTrabalhadas = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
        }

        System.out.printf("%-15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s%n", "Nome", "Salário Base", "Hora Extra", "Insalubridade", "Bonificação", "INSS", "Imposto Renda", "Salário Líquido");
        printSeparatorLine(150);

        for (int i = 0; i < NumFuncionarios; ++i) {
            float salarioBase = calcularSalarioBase(funcionarios[i].Departamento, funcionarios[i].HorasTrabalhadas);
            float horaExtra = calcularHoraExtra(funcionarios[i].Departamento, funcionarios[i].HorasTrabalhadas);
            float insalubridade = calcularInsalubridade(funcionarios[i].Departamento, salarioBase);
            float bonus = calcularBonus(salarioBase, funcionarios[i].Departamento, funcionarios[i].HorasTrabalhadas);
            float salarioBruto = salarioBase + horaExtra + insalubridade + bonus;
            float inss = calcularINSS(salarioBruto);
            float ir = calcularIR(salarioBruto);
            float salarioLiquido = calcularSalarioLiquido(salarioBruto);

            printTableRow(funcionarios[i].Nome, salarioBase, horaExtra, insalubridade, bonus, inss, ir, salarioLiquido);
        }

        printSeparatorLine(150);
        scanner.close();
    }
}
