import Image from "next/image";
import logoBlack from "@/public/imgs/logoBlack.png";
import Link from "next/link";

export default function Login() {
    return (
        <div className="w-full max-w-md flex flex-col justify-center items-center">
            <div className="text-center flex flex-col gap-2 px-4 items-center">
                <Image src={logoBlack} alt="logoImg" />
                <h1 className="text-5xl sm:text-4xl md:text-5xl font-normal">BookFlow</h1>
                <h2 className="text-xl sm:text-xl font-normal">Welcome Back !!</h2>
                <p className="text-zinc-700">Please enter your credentials to log in</p>
            </div>

            <div className="flex flex-col text-start w-full gap-2 mt-4">
                <input
                    className="p-3 border border-solid border-zinc-600 rounded-xl"
                    type="email"
                    placeholder="Email"
                />
                <input
                    className="p-3 border border-solid border-zinc-600 rounded-xl"
                    type="password"
                    placeholder="Password"
                />
                <div className="flex justify-between flex-col md:flex-row text-center md:text-lg text-base mt-1 ">
                    <small className="cursor-pointer underline decoration-slice">Forgot Password?</small>
                    <small className="cursor-pointer underline decoration-slice">
                        <Link href="/register">New to our platform? Register now.</Link>
                    </small>
                </div>
            </div>

            <button className="uppercase p-3 w-full text-white bg-black rounded-xl mt-4">Login</button>
        </div>
    );
}
