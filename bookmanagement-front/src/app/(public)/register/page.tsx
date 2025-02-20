import Image from "next/image";
import logoBlack from "@/public/imgs/logoBlack.png";
import Link from "next/link";

export default function Register() {
    return (
        <div className="flex flex-col justify-center items-center">
            <div className="text-center flex flex-col gap-2 px-4 items-center">
                <Image src={logoBlack} alt="logoImg" />
                <h1 className="text-5xl font-normal">BookFlow</h1>
                <h2 className="text-xl font-normal">Create an Account</h2>
                <p className="text-zinc-700">Join our platform to get started.</p>
            </div>

            <div className="flex flex-col text-start w-full gap-2 m-4">
                <input
                    className="p-3 border border-solid border-zinc-600 rounded-xl"
                    type="text"
                    placeholder="Username"
                />
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
                <small className="inline-block c underline decoration-slice text-center">
                    <Link className="ursor-pointer" href={"/login"}>
                        Already have Account? Login now.
                    </Link>
                </small>
            </div>

            <button className="uppercase p-3 w-full text-white bg-black rounded-xl">Register</button>
        </div>
    );
}
