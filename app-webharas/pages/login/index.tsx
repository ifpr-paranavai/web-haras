import {useForm} from 'react-hook-form';
import {ApiResponse} from '../../models/ApiResponse';
import {login} from "../../services/userService";
import {TokenJwt} from "../../models/TokenJwt";
import {checkIsValidAuthenticated, getUserByToken, justLogout, setToken} from "../../services/authService";
import UsuarioContext from "../../models/UsuarioContext";
import {Roles} from "../../constants";
import Input from "../../components/Input/Input";
import H1 from "../../components/Headings/H1";
import H2 from "../../components/Headings/H2";
import Button from "../../components/Button/Button";
import {useEffect, useState} from "react";

type Inputs = {
  usuario: string,
  senha: string
}

export default function Login() {
  const [loadingSubmit, setLoadingSubmit] = useState<boolean>(false);
  const { handleSubmit, register } = useForm();
  // let navigate = useNavigate();

  useEffect(() => {
    const isValidAuth = checkIsValidAuthenticated();
    if(isValidAuth) {
      //TODO - next navigate
      // navigate("/", {replace: true});
    } else {
      justLogout();
    }
  }, []);

  const onSubmit = async (data: any) => {
    setLoadingSubmit(true);
    const loginData = {
      usuario: data.usuario,
      senha: data.senha
    }

    const req: ApiResponse<TokenJwt> = await login(loginData);

    if(req.success) {
      setToken(req.data.token);

      const user: UsuarioContext = getUserByToken();

      if(user.roles?.includes(Roles.ADMIN)) {
        window.location.href = "/admin"
      } else {
        window.location.href = "/home"
      }
    } else {
      setLoadingSubmit(false);
    }
  }

  return (
    <>
      <section className="min-h-screen bg-gradient-to-tl from-orange-900 to-amber-700">
        <div className="px-0 py-20 mx-auto max-w-7xl sm:px-4">
          <div className="flex flex-col items-center mb-4">
            <H1 className="mb-0 font-bold text-white">Web Haras</H1>
            <p className="text-gray-300">Compra e Venda de Animais</p>
          </div>
          <div className="w-full px-4 pt-5 pb-6 mx-auto bg-white rounded-none shadow-xl sm:rounded-lg sm:w-10/12 md:w-8/12 lg:w-6/12 xl:w-4/12 sm:px-6">
            <H2>Realizar Login</H2>
            <form className={"mb-2 space-y-4"} onSubmit={handleSubmit(onSubmit)}>
              <div className={"block"}>
                <div className="col-span-1">
                  <Input
                      id={"usuario"}
                      field={"usuario"}
                      label={"Usuario"}
                      register={register}
                  />
                  <Input
                      id={"senha"}
                      type={"password"}
                      field={"senha"}
                      label={"Senha"}
                      register={register}
                  />
                </div>
              </div>
              <Button loading={loadingSubmit}>
                {loadingSubmit && (
                    <span className="spinner w-4 h-4" role="status" aria-hidden="true"></span>
                )}
                <span className="px-1">Login</span>
              </Button>
            </form>
          </div>
        </div>
      </section>
    </>
  )
}