// app/layout.tsx (Root Layout)
export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="pt-BR">
            <head>
                <title>BookManager</title>
            </head>
            <body>{children}</body>
        </html>
    );
}
