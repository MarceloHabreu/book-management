export default function AdminLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html>
            <head>
                <title>BookManager</title>
            </head>
            <body>
                <h1>Private</h1>
                {children}
            </body>
        </html>
    );
}
